async function generateTree() {
    // 用于配置 zTree 设置的对象
    const setting = {
        "view": {
            // 自定义 DOM 元素
            "addDiyDom": function (treeId, {icon, tId}) {
                $("#" + tId + "_ico")
                    .removeClass()
                    .addClass(icon)
            },
            // 当处于 Hover 状态调用的回调函数
            "addHoverDom": function (treeId, {id, tId, level, children}) {
                // 目标：添加一个按钮组可以操控元素
                let btnGroupId = tId + "_btnGrp"
                // 判断是否已经存在按钮组
                if ($("#" + btnGroupId).length > 0) {
                    return;
                }

                const addBtnHtml = "<a data-id='" + id + "' class='btn btn-info dropdown-toggle btn-xs add-btn' style='margin-left:10px;padding-top:0px;' href='#' >&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>"
                const editBtnHtml = '<a data-id="' + id + '" class="btn btn-info dropdown-toggle btn-xs edit-btn" style="margin-left:10px;padding-top:0px;"  href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>'
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

                // 找到需要附着在后面的 a 标签元素
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
        alert('获取服务器资源失败:' + message)
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