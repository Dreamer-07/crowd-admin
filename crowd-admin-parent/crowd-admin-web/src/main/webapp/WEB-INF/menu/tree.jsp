<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>(尚筹网)后台管理</title>
    <%@ include file="/WEB-INF/include/resources.jsp" %>
    <script type="text/javascript" src="axios/axios.min.js"></script>
    <script type="text/javascript" src="axios/qs.min.js"></script>
    <link rel="stylesheet" href="ztree/zTreeStyle.css">
    <script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="api/menu.js"></script>
    <script type="text/javascript" src="api/utils.js"></script>
    <script type="text/javascript">
        $(function () {
            let pid;
            let id;

            generateTree()

            $("#treeDemo").on('click', '.add-btn', function () {
                $('#menuAddModal').modal('show')
                pid = $(this).data('id')
                return false
            })

            $('#treeDemo').on('click', '.edit-btn', function () {
                $("#menuEditModal").modal('show')

                let zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
                let menuNodeId = id = $(this).data('id');

                let menuNodeInfo = zTreeObj.getNodeByParam("id", menuNodeId);
                let dataObj = extendObjAttr(menuNodeInfo, ['name', 'icon', 'url'])

                for (let key in dataObj) {
                    let value;
                    if (key === 'icon') {
                        value = [dataObj[key]]
                    } else {
                        value = dataObj[key]
                    }
                    $('#menuEditModalForm').find('[name=' + key + ']').val(value)
                }

                return false
            })

            $('#treeDemo').on('click', '.remove-btn', function () {
                $('#menuConfirmModal').modal('show')
                let zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
                let menuNodeId = id = $(this).data('id');

                let {name} = zTreeObj.getNodeByParam("id", menuNodeId);

                $('#removeNodeSpan').text("[" + name + "]")
                return false
            })

            $("#menuSaveBtn").click(function () {
                const menuAddModelForm = document.querySelector("#menuAddModalForm");
                const formData = new FormData(menuAddModelForm)
                formData.append("pid", pid)

                saveTreeNodeData(formData)
                    .then(({data: {success, message}}) => {
                        if (!success) {
                            alert('访问服务器失败:' + message)
                            return
                        }

                        generateTree()

                        menuAddModelForm.reset()
                    })
                    .catch(reason => {
                        console.error("请求失败:" + reason.message)
                    })
                    .finally(() => {
                        $('#menuAddModal').modal('hide')
                    })


            })

            $("#menuEditBtn").click(function () {
                const menuEditModelForm = document.querySelector("#menuEditModalForm");
                const formData = new FormData(menuEditModelForm)
                formData.append("id", id)

                editMenuNodeInfo(formData)
                    .then(({data: {success, message}}) => {
                        if (!success) {
                            alert('访问服务器失败:' + message)
                            return
                        }

                        menuEditModelForm.reset()
                    })
                    .catch(reason => {
                        console.error("请求失败:" + reason.message)
                    })
                    .finally(() => {
                        $('#menuEditModal').modal('hide')
                    })
            })

            $('#confirmBtn').click(function () {
                removeMenuNode(id)
                    .then(({data: {success, message}}) => {
                        if (!success) {
                            alert('访问服务器失败:' + message)
                            return
                        }

                        generateTree()
                    })
                    .catch(reason => {
                        console.error("请求失败:" + reason.message)
                    })
                    .finally(() => {
                        $('#menuConfirmModal').modal('hide')
                    })
            })
        })


    </script>
</head>
<body>
<%@ include file="/WEB-INF/include/nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include/sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/common/modal-menu-add.jsp" %>
<%@ include file="/WEB-INF/common/modal-menu-edit.jsp" %>
<%@ include file="/WEB-INF/common/modal-menu-confirm.jsp" %>
<script type="text/javascript">
</script>
</body>
</html>
