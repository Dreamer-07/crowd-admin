async function getAllAuth() {
    const {data: {success, message, data}} = await axios.get('auth/getAll');

    if (!success) {
        throw new Error('获取服务器数据失败:' + message)
    }

    return data
}

async function getAuthIdByRoleId(roleId) {
    const {data: {success, message, data}} = await axios.get(`assign/get/role/auth/${roleId}`);

    if (!success) {
        throw new Error('获取服务器数据失败:' + message)
    }

    return data
}

function saveRoleAuthRelation(roleId, authIds) {
    return axios.get('assign/role/to/auth/save', {
        params: {roleId, authIds},
        paramsSerializer: params => Qs.stringify(params, { indices: false })
    })
}