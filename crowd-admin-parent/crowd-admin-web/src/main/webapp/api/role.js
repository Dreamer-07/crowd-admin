// ���ɱ��
async function generateTable() {
    var pageInfo = await getPageInfoRemote();
    if (pageInfo) {
        loadTableData(pageInfo)
        loadPagination(pageInfo)
    }
}

// ��ȡ��ҳ����
async function getPageInfoRemote() {
    const {pageNum, pageSize, keyword, Qs} = window
    // ͨ�� axios �������󲢻�ȡ
    let {data: result} = await axios.post('role/page', Qs.stringify({pageNum, pageSize, keyword}), {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })

    if (!result.success) {
        alert('��ȡ��������Դʧ��')
        return;
    }
    return result.data;
}

// ���ر��
function loadTableData({list}) {
    if (list.length === 0 || !list) {
        $("#roleDataTableBody").append("<tr><td colspan='4'>��ʱû���������</td></tr>")
        return
    }
    // ��ձ��
    $("#roleDataTableBody").empty()
    for (let i = 0; i < list.length; i++) {
        $("#roleDataTableBody").append(`
            <tr>
                <td>${i}</td>
                <td><input data-id="${list[i].id}" type="checkbox" class="select-remove-role"></td>
                <td>${list[i].name}</td>
                <td>
                    <button data-id="${list[i].id}" type="button" class="btn btn-success btn-xs assign-auth-btn"><i
                            class="glyphicon glyphicon-check"></i></button>
                    <button data-id="${list[i].id}" type="button" class="btn btn-primary btn-xs edit-btn"><i
                            class="glyphicon glyphicon-pencil"></i></button>
                    <button data-id="${list[i].id}" type="button" class="btn btn-danger btn-xs remove-btn"><i
                            class="glyphicon glyphicon-remove"></i></button>
                </td>
            </tr>
        `)
    }
}

// ���ط�ҳ��
function loadPagination({total, pageSize, pageNum}) {
    // ��ҳ��
    var num_entries = total;
    // ������ҳ
    $("#Pagination").pagination(num_entries, {
        num_edge_entries: 2, //��Եҳ��
        num_display_entries: 4, //����ҳ��
        callback: selectPageNumCallback, // �ص�����
        items_per_page: pageSize, //ÿҳ��ʾ����
        current_page: pageNum - 1, // ��ǰҳ��
        prev_text: "��һҳ",
        next_text: "��һҳ"
    });
}

// �����ҳ��ť�ص�
function selectPageNumCallback(pageNum) {
    window.pageNum = pageNum + 1;
    generateTable();
    return false;
}

function removeRoleByIds(ids) {
    return axios.post('role/remove', ids)
}

// �����йؽ�ɫ��Ȩ����
async function generateRoleAuthTree(roleId) {
    // ��ȡ��������
    const allAuthList = await getAllAuth()

    // zTree ���νṹ����
    const zTreeSetting = {
        data: {
            simpleData: {
                enable: true,
                pIdKey: "categoryId"
            },
            key: {
                name: "title"
            }
        },
        check: {
            enable: true
        }
    }

    // ������
    $.fn.zTree.init($("#authTreeDemo"), zTreeSetting, allAuthList)

    // ��ȡzTree����
    const zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");

    // չ��ȫ��
    zTreeObj.expandAll(true)

    // ��ȡ��ǰѡ��� ��ɫ(role) ������ Ȩ��(auth)
    const authIds = await getAuthIdByRoleId(roleId)

    // ��ѡ�Ѿ�������Ȩ��(auth)
    const check = true
    const checkTypeFlag = false
    authIds.forEach(authId => {
        const authNodeInfo = zTreeObj.getNodeByParam("id", authId);
        zTreeObj.checkNode(authNodeInfo, check, checkTypeFlag)
    })
}