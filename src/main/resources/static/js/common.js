// 设置命名空间
var Common = {
    /**
     * 简单遍历树的节点，父节点会比子节点先访问
     */
    traverseTree: function (treeNodes, callback) {
        if (treeNodes && treeNodes.length) {
            var stack = [treeNodes];
            while (stack.length) {
                var nodes = stack.pop();
                nodes.forEach(function (node) {
                    callback(node);
                    if (node.children) {
                        stack.push(node.children);
                    }
                });
            }
        }
    }
};

