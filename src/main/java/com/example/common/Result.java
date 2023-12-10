package com.example.common;
/*
 * 这个Result类的主要作用是对API或函数调用的返回结果进行封装。
 * 它包含一个泛型字段data用于存储返回的具体数据，以及两个字段code和msg分别用于存储结果的状态码和状态信息。
 * 这个类提供了一些静态工厂方法，如success()、success(T data)、error()、error(String code, String msg)等，以方便创建不同类型的Result对象。
 * 同时，这个类也提供了相应的getter和setter方法，以便于外部代码获取和设置Result对象的状态和数据。
 */
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    private Result(T data) {
        this.data = data;
    }

    public Result() {
    }

    public static Result success() {
        Result tResult = new Result<>();
        tResult.setCode(ResultCode.SUCCESS.code);
        tResult.setMsg(ResultCode.SUCCESS.msg);
        return tResult;
    }

    public static <T> Result<T> success(T data) {
        Result<T> tResult = new Result<>(data);
        tResult.setCode(ResultCode.SUCCESS.code);
        tResult.setMsg(ResultCode.SUCCESS.msg);
        return tResult;
    }

    public static Result error() {
        Result tResult = new Result<>();
        tResult.setCode(ResultCode.ERROR.code);
        tResult.setMsg(ResultCode.ERROR.msg);
        return tResult;
    }

    public static Result error(String code, String msg) {
        Result tResult = new Result<>();
        tResult.setCode(code);
        tResult.setMsg(msg);
        return tResult;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
