package com.example;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ExpressionVisitor implements ExpressionParserVisitor {
    private final LinkedList<Object> stack = new LinkedList<>();
    private final Map<String, Object> symbolTable = new HashMap<>();

    public Object visit(SimpleNode node, Object data) {
        node.childrenAccept(this, data);
        return null;
    }

    public Object visit(ASTstart node, Object data) {
        node.childrenAccept(this, data);
        return symbolTable;
    }

    public Object visit(ASTAddExpr node, Object data) {
        node.childrenAccept(this, data);
        Integer arg1 = pop();
        Integer arg2 = pop();
        stack.addFirst(arg2 + arg1);
        return null;
    }

    public Object visit(ASTSubractExpr node, Object data) {
        node.childrenAccept(this, data);
        Integer arg1 = pop();
        Integer arg2 = pop();
        stack.addFirst(arg2 - arg1);
        return null;
    }

    public Object visit(ASTMultiplyExpr node, Object data) {
        node.childrenAccept(this, data);
        Integer arg1 = pop();
        Integer arg2 = pop();
        stack.addFirst(arg2 * arg1);
        return null;
    }

    public Object visit(ASTDivideExpr node, Object data) {
        node.childrenAccept(this, data);
        Integer arg1 = pop();
        Integer arg2 = pop();
        stack.addFirst(arg2 / arg1);
        return null;
    }

    public Object visit(ASTNegateExpr node, Object data) {
        node.childrenAccept(this, data);
        Integer arg1 = pop();
        stack.addFirst(-1 * arg1);
        return null;
    }

    public Object visit(ASTNumber node, Object data) {
        node.childrenAccept(this, data);
        stack.addFirst(node.data.get("value"));
        return null;
    }

    public Object visit(ASTStatement node, Object data) {
        node.childrenAccept(this, data);
        Integer value = (Integer) stack.removeFirst();
        String var = (String) stack.removeFirst();
        symbolTable.put(var, value);
        return null;
    }

    public Object visit(ASTVariable node, Object data) {
        node.childrenAccept(this, data);
        String var = (String) node.data.get("name");
        stack.addFirst(var);
        return null;
    }

    public Object visit(ASTVariableValue node, Object data) {
        node.childrenAccept(this, data);
        String var = (String) node.data.get("name");
        stack.addFirst(symbolTable.get(var));
        return null;
    }

    private Integer pop() {
        return (Integer) stack.removeFirst();
    }
}
