# Aurora总结

## 一. 开发的基本步骤

1. 在功能定义中创建一个功能
2. 创建screen/svc并编写，在页面注册功能中进行定义
3. 在对应的功能下进行页面分配
4. 创建bm，进行bm分配
5. 在角色定义中给角色分配功能
6. 缓存重载

## 二. 命名规则

### 脚本管理

- table								大写表名.sql
- seq(序列)						大写表名_S.sql
- view								大写表名_vl.sql
- pkg								  大写表名_pkg.sql
- classes						    小写表名_define.bm
- modules						 小写表名_define.screen/svc
- prompt(系统描述)		 表名.sql
- syscode(系统代码)		系统代码.sql
- fun(功能定义)			    功能号.sql
- Sysmessage(消息代码)		消息代码.sql

### 表

- 表名命名(长度不能超过30):   大写功能号_英文表意；
- 主键命名(长度不能超过30)：大写表名__PK;_
- 视图命名(长度不能超过30)：大写表名__VL;_
- 序列命名(长度不能超过30)：大写表名_S；
- 唯一索引命名(长度不能超过30)：大写表名_U1;   编码字段、代码字段
- 一般索引命名(长度不能超过30)：大写表名_N1；   外键、enabled_id(启用标志)

### 包

- 包名(长度不能超过30)：表名_pkg
- 成员变量((长度不能超过30)):  v_开头
- 游标命名：C_开头
- 参数：        p_开头
- 异常定义：e_ exception
- 存储过程命名(length<30)(对exp_report_headers表操作)
  - insert:insert_exp_report_headers
  - update:update_exp_report_headers
  - delete:delete_exp_report_headers
  - check:check_exp_report_headers
- 存储过程参数命名(参数类型只有varchar2,number,date)
  - p _参数 in default null
  - p _参数 in out
  - x _参数 out
- 函数命名：get_
- type类型：t_开头

### screen页面

- js函数：大写功能号_用途Fun
- init_procedure下的rootPath：大写功能号_开头
- dataSet的id
  - 结果集：大写功能号_小写表名__result_ds
  - 查询集：大写功能号_小写表名__query_ds
  - 下拉框结果集：大写功能号_小写字段名__get_data_ds
- grid的id：大写功能号 _ ~~~ _ grid
- 编辑组件的id：大写功能号_ 开头_组件简称结尾
- 其他需要的id：大写功能号_ 开头_对应结尾

### 常用按钮系统描述

  ​	新增按钮:			   HAP_NEW

  ​	删除按钮：		    HAP_DELETE

  ​	清除按钮：			HAP_CLEAR

  ​	保存按钮：			HAP_SAVE

  ​	重置按钮：			HAP_RESET

## 三. screen页面

### lov选择框

lov框的指定不同于hap的配置，其配置相对来说更简单，用法如下

- screen页面

  - 通过lovService属性绑定对应的查询bm
  - 通过autoComplete，autoCompleteField指定页面输入自动查询的查询条件
  - 通过<a:map from="" to=""/>将bm查询得到的数据映射到当前数据集对应字段中

  ```xml
  <a:dataSet id="hrms_person_ds" selectable="true">
                  <a:fields>
                      <a:field name="resident_number" autoComplete="true" autoCompleteField="resident_number" lovGridHeight="320" lovHeight="500" lovService="hrms.HRMS1040.hrms_check_in_person_lov" lovWidth="630" prompt="HRMS_CHECK_IN.RESIDENT_CODE" required="true" title="HRMS_CHECK_IN.RESIDENT_CODE">
                          <a:mapping>
                              <a:map from="resident_number" to="resident_number"/>
                              <a:map from="resident_id" to="resident_id"/>
                          </a:mapping>
                      </a:field>
                      <a:field name="resident_id"/>
                  </a:fields>
              </a:dataSet>
  ```

  - js给lov选择框指定bm路径
  
  ```js
  record.getField('partner_name').setLovService('acp.acp_partner_name');
  ```
  
  - js给lov选择框传入查询值
  
  ```js
  record.getField('partner_name').setLovPara('partner_category', value);
  ```

- bm文件


  [参考bm相关中与lov框有关点](#bm相关)

### 系统代码下拉框

主要用于ComboBox，也即下拉框。

涉及两张表：
sys_codes : 代码表
sys_code_values：代码值表

使用：

- 首先进行代码维护，创建对应的代码，及代码值
- 页面中查询代码值，封装进dataset

```xml
<a:dataSet id="sys_function_type_ds" lookupCode="FUNCTION_TYPE"/>
```

- ResultDataSet或者QueryDateSet中使用

```xml
<a:field name="function_type_display" displayField="code_value_name"
options="sys_function_type_ds" required="true" returnField="function_type"
valueField="code_value"/>
```

- Grid中引用DataSet，并使用comboBox这个editor即可

结合包 sys_code_pkg 与 sys_codes sys_code_values 表中字段描述即可值知参数含义

### 非系统代码下拉框

当comboBox的数据不是系统代码，而来源于某一张表的数据时

使用：

- 页面中查询代码值，封装进dataset

```xml
<a:dataSet id="" fetchAll="true" loadData="true" model="bm路径"/>
```

- ResultDataSet或者QueryDateSet中使用

```xml
<a:field name="currency_name" displayfield="currency_name" options="" prompt="" returnField="currency_code" valueField="currency_code"/>
<a:field name="currency_code"/>
```

- Grid中引用DataSet，并使用comboBox这个editor即可

### 打开子页面

```js
function addUserInfo(){
	new Aurora.Window({id:'sys_user_create_window',
                       url:'sys_user_create.screen',
                       title:'新增用户',
                       height:259,
                       width:510});
}
```

- id:关闭窗口需要用到id
- url:screen页面路径
- title:窗口标题
- fullscreen:'true'  全屏
- height：子页面高度
- width：子页面宽度

### event

- dataSet
  - add 新增事件：新增时从头结构获取数据放入行结果时需要触发
  - update 修改事件：与修改操作有关的事件
  - submitsuccess 提交成功事件
  - submiterror 提交失败事件
  - load 加载事件
  - indexchange 光标改变事件
  - beforeCreate 创建之前事件
  - 更多事件<http://aurora.hand-china.com/doc/jsdoc/index.html>
- grid
  - cellclick 单元格点击事件
  - rowclick 行点击事件
  - 更多事件<http://aurora.hand-china.com/doc/jsdoc/index.html>
- 编辑组件
  - focus 获得焦点事件
  - blur 失去焦点事件
  - 更多事件<http://aurora.hand-china.com/doc/jsdoc/index.html>

### link链接设置

- 指定其他screen或者svc路劲

```xml
<a:link id="uploadFile_link_10" url="${/request/@context_path}/uploadFile.screen"/>
```

- 指定bm路径

  其中modelaction字段指定具体的操作，对应bm中operation的name(execute,insert,update,batch_update等)

```xml
<a:link id="exp_report_submit_link" model="db.exp_report_pkg.submit_exp_report" modelaction="execute"/>
```

### 页面加载时获得初始值

使用如下标签在页面加载时执行相关的查询操作，其中`defaultWhereClause`属性设置过滤条件

```xml
<a:init-procedure>
        <a:model-query defaultWhereClause="gc_lov.currency_code=&apos;CNY&apos;" model="hrms.HRMS1030.hrms_currency_lov" rootPath="HRMS1030_get_currency_name"/>
</a:init-procedure>
```

使用

```xml
<a:field name="currency" defaultValue="${/model/HRMS1030_get_currency_name/record/@currency_code}" valueField="currency"/>
```

### 页面js,编辑组件大杂烩

- 数字输入框设置不可为负数

```xml
<a:numberField name="total_money" allowNegative="false" bindtarget="HRMS1030_contract_ds"/>
```

- js设置编辑组件只读

```js
record.getField('credit_cur').setReadOnly(true);
```

- 设置按钮可点击

```js
$('GLD5010_voucher_submit_btn').enable();
```

- 编辑组件自定义校验属性

```xml
<a:field name="requisition_date_f" validator="dateValidator"/>
```

```js
function dateValidator(record, name, value) { //日期校验方法
                if (name == 'requisition_date_f' || name == 'requisition_date_t') {
                    var start_date = record.get('requisition_date_f');
                    var end_date = record.get('requisition_date_t');
                    if ( !! end_date) { //由于结束日期非必填，只有在结束日期填了才进行比较
                        if (!compareDate(start_date, end_date)) {
                            return '${l:START_GREATER_THAN_END}'; //校验不通过返回字符串
                        }
                    }
                    return true; //校验通过返回true
                }
            }
```

- 查询表单需要绑定查询集与结果集，其中查询集提供样式，结果集与查询bm相关联

```xml
<a:queryForm bindTarget="hrms_contract_query_ds" resultTarget="hrms_contract_result_ds">
</a:queryForm>
```

- 控件中以下两属性可控制滚动条的消失
  - marginHeight="110"
  - marginWidth="300"
- js判断变量是否undefined

```js
a==undefined
```

- 多参数拼接为a链接

```js
return '<a href="javascript:openEditWindow(' + contract_id+','+house_id+ ')">' + value + '</a>';
```

其中openEditWindow(contract_id,house_id)为跳转链接函数

```js
function openEditWindow(contract_id, house_id) {
                new Aurora.Window({
                    id: 'HRMS1030_new_window',
                    url: $('hrms_contract_new_link').getUrl() + '?contract_id=' + contract_id + '&house_id=' + house_id,
                    title: '${l:GLD_GL_ENTRY_DETIAL}',
                    fullScreen: true
                });
            }
```

- url拼接参数

```js
$('hrms_contract_new_link').getUrl() + '?contract_id=' + contract_id + '&house_id=' + house_id;
```

- 查询页刷新(对应结果集刷新)

```js
$('hrms_contract_result_ds').query();
```

- Aurora跳转页面时拼接json

```js
Aurora.request({
						para: {
							contract_id: contract_id,
							house_id: house_id
						},
					});
```

- 校验输入值合法性

```js
var dsh = $('HRMS1030_house_result_ds');
		if (!ds.validate()) {
			return;
		}
```

- 动态编辑组件

js函数，根据相关判定决定编辑组件的类别

```js
function CSH1075_cashFlowItemTypeIdEdit(record, name) {
            if (record.isNew) {
                return 'CSH1075_cashFlow_lov';
            }
            return '';
        }
```

组件绑定

```xml
<a:column name="cashFlowItemDesc" editorFunction="CSH1075_cashFlowItemTypeIdEdit" prompt="csh_mo_pay_usd_ref_flow_it.cash_flow_item_id" width="250"/>

<a:lov id="CSH1075_cashFlow_lov"/>
```

### placeHolder占位符

```xml
<a:placeHolder id="numberfield_holder"/>
```

需要引入标签

```xml
<a:screen xmlns:c="aurora.application.action" xmlns:p="uncertain.proc" xmlns:a="http://www.aurora-framework.org/application">
```

- 作用：

  - 占位符可以实现对dataSet，grid，column，edit等组件的隐显或者属性的修改
  - 可参考`exp_report_maintain_standard.screen`页面

- 操作：

  - 通过screen页面的`< a:init-procedure>< /a:init-procedure>`标签对相关的判断条件进行查询

  ```xml
  <a:init-procedure>
          <a:model-query autoCount="false" fetchAll="true" model="expm.exp_report_head_show" rootPath="head_data"/>
  </a:init-procedure>
  ```

  - 在需要动态改变的`dataSet，grid，column，editor`等标签中设置占位符

  ```xml
  <a:editors>
  		<a:placeHolder id="numberfield_holder"/>
  </a:editors>
  ```

  - 通过`<c:create-config ></c:create-config >`标签根据初始化查询决定占位符隐现与属性修改

  ```xml
  <!-- 调整类报销单的金额可以为负数 -->
  	<c:create-config targetId="numberfield_holder">
  		<!--test 传入默认值-->
  		<p:switch test="/model/head_data/record/@adjustment_flag">
  			<p:case value="Y">
  				<c:process-config>
  					<a:numberField id="numberfield" allowNegative="true" decimalPrecision="3"/>
  				</c:process-config>
  			</p:case>
  			<p:case>
  				<c:process-config>
  					<a:numberField id="numberfield" allowNegative="false" decimalPrecision="3"/>
  				</c:process-config>
  			</p:case>
  		</p:switch>
  	</c:create-config>
  ```

  - 一个包含loop，switch-case的参考代码

  ```xml
  <c:create-config targetId="dynamicLineColumnObj">
              <p:loop source="/model/line_object">
                  <p:switch test="@expense_object_method">
                      <p:case value="VALUE_LIST">
                          <c:process-config>
                              <a:column name="${@expense_object_type_code}" align="center" editor="company_combox1"/>
                          </c:process-config>
                      </p:case>
                  </p:switch>
              </p:loop>
          </c:create-config
  ```

## 四. svc作用

1. 保持数据一致性；

2. 批量处理数据；

3. 只要有一个bm出现问题，整个svc回滚

## 五. bm相关

### 命名

- 表名.bm
- 表名_define.bm

### 知识点

- 涉及金额时数据库字段为number类型，bm中应为datatype="java.lang.Long.Double"

- <<bm:query-fields>>只作用于查询栏，需要查询按钮才能触发，给查询sql拼接where条件

- <<bm:data-filters>>不需要媒介触发，进入页面自动触发，直接给查询语句拼接where条件

- 当bm作为lov框的使用对象时

  - 需要使用<bm:fields ></bm:fields >将查询所得所有字段列出
  - 如果需要在lov框做查询，需要在对应的<bm:field >字段添加forQuery="true"
  - 如果需要在lov框展示，需要在对应的<bm:field >字段添加fordisplay="true"
  - 同时lov框的样式可通过相关属性进行改变

  ```xml
   <bm:field name="currency_code" databaseType="VARCHAR2" datatype="java.lang.String" displayWidth="150" forQuery="true" fordisplay="true" physicalName="CURRENCY_CODE" prompt="GLD_CURRENCY.CURRENCY_CODE"/>    
  ```

- query-field使用queryExpression拼接查询条件时

  - 大于(去掉null)      &gtnull;

  ```xml
   <bm:query-field name="contract_start_date" queryExpression="contract_start_date &gt;=${@contract_start_date}"/>
  ```

  - 小于(去掉null)      &ltnull;

  ```xml
  <bm:query-field name="contract_end_date" queryExpression="contract_end_date &lt;=${@contract_end_date}"/>
  ```

  - 模糊查询

  ```xml
   <bm:query-field name="agent_linkman" queryExpression="agent_linkman like &apos;%&apos;||${@agent_linkman}||&apos;%&apos;"/>
  ```

  - 普通查询

  ```xml
  <bm:query-field name="house_type" queryExpression="house_type=${@house_type}"/>
  ```

### 期间下拉框可参考使用的bm

可根据下面查询语句中操作的gld_periods表进行自定义查询

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--
    $Author: jirong.feng 
    $Date: 2019-7-25 下午11:57:10  
    $Revision: 1.0  
    $Purpose: 期间查询
-->
<bm:model xmlns:bm="http://www.aurora-framework.org/schema/bm">
    <bm:operations>
        <bm:operation name="query">
            <bm:query-sql><![CDATA[ 
            	SELECT
                    a.period_name
                FROM
                    gld_periods a
                WHERE
                    a.period_year     = 2019 AND
                    a.period_set_code = '17' AND
                    a.adjustment_flag = 'N']]></bm:query-sql>
        </bm:operation>
    </bm:operations>
    <bm:fields>
        <bm:field name="period_name" databaseType="VARCHAR2" datatype="java.lang.String" physicalName="PERIOD_NAME" required="true"/>
    </bm:fields>
</bm:model>
```

## 六. 头行结构相关

​	头行结构一条头数据绑定多条行数据，具体操作如下

- screen页面
  
  - 头结果集submitUrl指定进行头行存储对应的svc
  
  ```xml
  <a:dataSet id="GLD1010_gl_entry_header_ds" autoCreate="true" autoQuery="true" queryUrl="${/request/@context_path}/autocrud/gld.GLD1010.gld_gl_head/query?head_id=${/parameter/@head_id}" submitUrl="${/request/@context_path}//gld/GLD1010/gld_gl_save.svc">
  </a:dataSet>
  ```
  
  - 行结果集添加bindName="lines"与bindTarget="GLD1010_gl_entry_header_ds"属性，指定行结果集为头结果集lines字段数据
  
  ```xml
   <a:dataSet id="GLD1010_gl_entry_lines_ds" autoQuery="true" bindName="lines" bindTarget="GLD1010_gl_entry_header_ds" fetchAll="false" pageSize="20" queryUrl="${/request/@context_path}/autocrud/gld.GLD1010.gld_gl_lines/query?head_id=${/parameter/@head_id}" selectable="true">
  </a:dataSet>              
  ```
  
- svc文件

  svc文件通过提交的json数据中的_status状态判断进行插入或者更新操作，分别指定对应的bm存储过程。

  `本文操作采用封装插入与删除同一个存储过程`

  ```xml
  <a:service xmlns:a="http://www.aurora-framework.org/application" xmlns:p="uncertain.proc" checkSessionLock="true" lockKey="${/session/@session_id}" trace="true">
      <a:init-procedure>
          <a:batch-apply sourcepath="/parameter">
              <!--<p:switch test="@current_parameter/@_status">
                  <p:case value="insert">
                      <a:model-insert model="gld.GLD1010.gld_gl_head"/>
                  </p:case>
                  <p:case value="update">
                      <a:model-update model="gld.GLD1010.gld_gl_head"/>
                  </p:case>
              </p:switch>-->
              <a:model-update model="gld.GLD1010.gld_gl_head"/>
              <a:batch-apply sourcepath="@current_parameter/lines">
                  <p:switch test="@current_parameter/@_status">
                      <p:case value="insert">
                          <a:model-insert model="gld.GLD1010.gld_gl_lines"/>
                      </p:case>
                      <p:case value="update">
                          <a:model-update model="gld.GLD1010.gld_gl_lines"/>
                      </p:case>
                  </p:switch>
              </a:batch-apply>
          </a:batch-apply>
      </a:init-procedure>
      <a:service-output output="/parameter"/>
  </a:service>
  ```

- bm文件

  - 头表的插入存储过程

  因为从表需要使用头表id作为关联外键，因此插入的存储过程需要将p_head_id参数设置为in out类型，即可以将id传入后台做更新操作，也可在进行插入操作时将`头表id`返回到bm中。

  返回的id使用<bm:parameter name="id" output="true" outputPath="@head_id"/>接受。

  ```xml
  <bm:operation name="update">
              <bm:update-sql><![CDATA[
                  BEGIN
  gld_gl_entry_24751_pkg.update_gl_entry(	p_head_id    => ${@id},
                          p_certificate_code  => ${@certificate_code},
                          p_between_date      => ${@between_date},
                          p_accessory_count   => ${@accessory_count},
                          p_currencies_of     => ${@currency_code},
                          p_currency_type     => ${@currency_type},
                          p_rate              => ${@rate},
                          p_notes             => ${@notes},
                          p_user_id           => ${/session/@user_id});
                  END;
              ]]></bm:update-sql>
              <bm:parameters>
                  <bm:parameter name="id" output="true" outputPath="@head_id"/>
              </bm:parameters>
          </bm:operation>
  ```

  - 从表的插入存储过程

  从表获取头表的id方法如下代码，通过两次返回获得svc逻辑下的头表指定`<bm:parameter name="id" output="true" outputPath="@head_id"/>`中的头表id

  ```xml
  <bm:operation name="insert">
              <bm:update-sql><![CDATA[
                  BEGIN
  gld_gl_entry_24751_pkg.insert_lines(p_head_id              =>   ${../../@head_id},
                           p_notes                =>   ${@notes},
                           p_accountant_name      =>   ${@accountant_name},
                           p_debit_original_cur   =>   ${@debit_original_cur},
                           p_credit_original_cur  =>   ${@credit_original_cur},
                           p_debit_cur            =>   ${@debit_cur},
                           p_credit_cur           =>   ${@credit_cur},
                           p_user_id      	    =>   ${/session/@user_id});
                  END;
              ]]></bm:update-sql>
          </bm:operation>
  ```

