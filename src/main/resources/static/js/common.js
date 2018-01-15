// 设置命名空间
var Common = {
    /**
     * 递归遍历树的节点，符合前序遍历顺序
     */
    traverseTree: function (treeNodes, callback) {
        if (treeNodes && treeNodes.length) {
            treeNodes.forEach(function (node) {
                callback(node);
                if (node.children) {
                    Common.traverseTree(node.children, callback);
                }
            });
        }
    }
};

