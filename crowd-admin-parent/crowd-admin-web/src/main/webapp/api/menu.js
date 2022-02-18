async function generateTree() {
    // �������� zTree ���õĶ���
    const setting = {
        "view": {
            // �Զ��� DOM Ԫ��
            "addDiyDom": function (treeId, {icon, tId}) {
                $("#" + tId + "_ico")
                    .removeClass()
                    .addClass(icon)
            },
            // ������ Hover ״̬���õĻص�����
            "addHoverDom": function (treeId, {id, tId, level, children}) {
                // Ŀ�꣺���һ����ť����Բٿ�Ԫ��
                let btnGroupId = tId + "_btnGrp"
                // �ж��Ƿ��Ѿ����ڰ�ť��
                if ($("#" + btnGroupId).length > 0) {
                    return;
                }

                const addBtnHtml = "<a data-id='" + id + "' class='btn btn-info dropdown-toggle btn-xs add-btn' style='margin-left:10px;padding-top:0px;' href='#' >&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>"
                const editBtnHtml = '<a data-id="' + id + '" class="btn btn-info dropdown-toggle btn-xs edit-btn" style="margin-left:10px;padding-top:0px;"  href="#" title="�޸�Ȩ����Ϣ">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>'
                const removeBtnHtml = '<a data-id="' + id + '" class="btn btn-info dropdown-toggle btn-xs remove-btn" style="margin-left:10px;padding-top:0px;" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>'
                let btnHtml = ''

                switch (level) {
                    case 0:
                        btnHtml = addBtnHtml
                        break;
                    case 1:
                        btnHtml = addBtnHtml + " " + editBtnHtml
                        if (children.length === 0) {
                            btnHtml += " " + removeBtnHtml
                        }
                        break;
                    case 2:
                        btnHtml = editBtnHtml + " " + removeBtnHtml
                        break;
                }

                // �ҵ���Ҫ�����ں���� a ��ǩԪ��
                let anchorId = tId + "_a"
                $("#" + anchorId).after("<span id='" + btnGroupId + "'>" + btnHtml + "</span>")
            },
            "removeHoverDom": (treeId, {tId}) => $("#" + tId + "_btnGrp").remove(),


        },
        "data": {
            "key": {
                "url": "not"
            }
        }
    }

    const {data: {success, message, data}} = await getTreeData()

    if (!success) {
        alert('��ȡ��������Դʧ��:' + message)
        return;
    }

    let stack = [data]
    while (stack.length) {
        let menuItem = stack.pop()
        menuItem.open = true
        let menuItemChildren = menuItem.children
        for (let i = menuItemChildren.length - 1; i >= 0; i--) {
            stack.push(menuItemChildren[i])
        }
    }

    $.fn.zTree.init($("#treeDemo"), setting, data)
}

function getTreeData() {
    return axios.post('menu/tree')
}

function saveTreeNodeData(treeNodeData) {
    return axios.post('menu/save', treeNodeData)
}

function editMenuNodeInfo(menuNodeInfo) {
    return axios.post('menu/edit', menuNodeInfo)
}

function removeMenuNode(menuId) {
    return axios.post(`menu/remove/${menuId}`)
}