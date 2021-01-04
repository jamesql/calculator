package com.calculator;

import com.calculator.utils.MathOps;

import java.util.function.Function;

import static com.calculator.utils.CheckForCalculationErrors.*;

public class Evaluator implements Expression.Visitor<Double> {

    public void solve(Expression expr) {
        try{
            System.out.println(evaluate(expr));
        } catch(Error e) {
            System.out.println(e.getMessage());
        }
    }

    public double evaluate(Expression expr) {
        return expr.accept(this);
    }

    @Override
    public Double visitLiteralNode(Expression.Literal expr) { return Double.parseDouble(expr.getValue()); }

    @Override
    public Double visitFunctionNode(Expression.Function expr) {
        double arg = evaluate(expr.getArgument());
        Function<Double, Double> result = MathOps.functions.get(expr.getFunction().getType());
        if(result == null) throw new Error("Failure in a Function Node, probably because you used a function that isn't implemented yet");
        return result.apply(arg);
    }

    @Override
    public Double visitUnaryNode(Expression.Unary expr) {
        double sideExpr = evaluate(expr.getSideExpr());

        return switch (expr.getOperator().getType()) {
            case MINUS -> -1.0 * sideExpr;
            case FACTORIAL -> MathOps.factorial(String.valueOf(sideExpr));
            default -> throw new Error("Failure in a Unary Node");
        };


    }


    @Override
    public Double visitGroupingNode(Expression.Grouping expr) {
        if(expr.getType().equals("grouping")) {
            return evaluate(expr.getExpression());
        }
        else if(expr.getType().equals("abs")) {
            return Math.abs(evaluate(expr.getExpression()));
        }

        //Unreachable
        throw new Error("Failure in a Grouping Node");
    }


    @Override
    public Double visitBinaryNode(Expression.Binary expr) {
        double left = evaluate(expr.getLeft());
        double right = evaluate(expr.getRight());

        switch (expr.getOperator().getType()) {
            case MINUS:
                return left - right;
            case PLUS:
                return left + right;
            case SLASH:
                checkArithmeticErrors(expr.getOperator(), left, right);
                return left / right;
            case STAR:
                return left * right;
            case EXP:
                return Math.pow(left, right);
            case MODULO:
                return left % right;
        }
        // Unreachable.
        throw new Error("Failure in a Binary Node");
    }
}