// 生成表格
async function generateTable() {
    var pageInfo = await getPageInfoRemote();
    if (pageInfo) {
        loadTableData(pageInfo)
        loadPagination(pageInfo)
    }
}

// 获取分页数据
async function getPageInfoRemote() {
    const {pageNum, pageSize, keyword, Qs} = window
    // 通过 axios 发送请求并获取
    let {data: result} = await axios.post('role/page', Qs.stringify({pageNum, pageSize, keyword}), {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })

    if (!result.success) {
        alert('获取服务器资源失败')
        return;
    }
    return result.data;
}

// 加载表格
function loadTableData({list}) {
    if (list.length === 0 || !list) {
        $("#roleDataTableBody").append("<tr><td colspan='4'>暂时没有相关数据</td></tr>")
        return
    }
    // 清空表格
    $("#roleDataTableBody").empty()
    for (let i = 0; i < list.length; i++) {
        $("#roleDataTableBody").append(`
            <tr>
                <td>${i}</td>
                <td><input data-id="${list[i].id}" type="checkbox" class="select-remove-role"></td>
                <td>${list[i].name}</td>
                <td>
                    <button type="button" class="btn btn-success btn-xs"><i
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

// 加载分页栏
function loadPagination({total, pageSize, pageNum}) {
    // 总页数
    var num_entries = total;
    // 创建分页
    $("#Pagination").pagination(num_entries, {
        num_edge_entries: 2, //边缘页数
        num_display_entries: 4, //主体页数
        callback: selectPageNumCallback, // 回调函数
        items_per_page: pageSize, //每页显示项数
        current_page: pageNum - 1, // 当前页码
        prev_text: "上一页",
        next_text: "下一页"
    });
}

// 点击分页按钮回调
function selectPageNumCallback(pageNum) {
    window.pageNum = pageNum + 1;
    generateTable();
    return false;
}

function removeRoleByIds(ids) {
    return axios.post('role/remove', ids)
}