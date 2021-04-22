# PLSQL总结

## plsql脚本获取

获取plsql创建功能定义，系统代码，系统描述，消息代码的脚本

```plsql
begin
--获取功能定义
dev_tool_pkg.create_fun_registry();

--获取系统代码
dev_tool_pkg.create_sys_code_registry();

--获取系统描述
dev_tool_pkg.create_sys_prompt_registry();

--获取消息代码
dev_tool_pkg.create_sys_message_registry();

end;
```

## 