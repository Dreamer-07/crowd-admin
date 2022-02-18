const extendObjAttr = (sourceObj, attrNameArr) => attrNameArr.reduce(
    (iter, val) => (val in sourceObj && (iter[val] = sourceObj[val]), iter),
    {}
)