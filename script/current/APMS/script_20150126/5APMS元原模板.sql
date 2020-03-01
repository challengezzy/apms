-- 模板生成脚本[T_APU_SWAPLOG]
-- ================start===========================================================
-- 删除模板明细数据
delete pub_templet_1_item where pk_pub_templet_1 in (select pk_pub_templet_1 from pub_templet_1 where templetcode = 'T_APU_SWAPLOG');
-- 删除模板分组信息
delete from PUB_TEMPLET_1_ITEM_GROUP where templetid in (select pk_pub_templet_1 from pub_templet_1 where templetcode = 'T_APU_SWAPLOG');
-- 判断是否已经存在模板
DECLARE TEMPL_COUNT NUMBER(6);
BEGIN
  SELECT COUNT(*) INTO TEMPL_COUNT FROM pub_templet_1 where templetcode ='T_APU_SWAPLOG';
  IF TEMPL_COUNT > 0 THEN
    -- 更新老的模板，ID没有变化
    UPDATE PUB_TEMPLET_1 SET  templetcode='T_APU_SWAPLOG', templetname='T_APU_SWAPLOG', tablename='V_L_APU_SWAPLOG', datasourcename='datasource_apms', pkname='ID', pksequencename='S_L_APU_SWAPLOG', savedtablename='L_APU_SWAPLOG', cardcustpanel='APMS', listcustpanel=null, dataconstraint=null, parent_pub_template=null, comefrom='0', ordersetting='SWAP_DATE DESC' WHERE templetcode = 'T_APU_SWAPLOG';
  ELSE
    -- 插入新模板，新生成ID
    Insert Into PUB_TEMPLET_1 (pk_pub_templet_1, templetcode, templetname, tablename, datasourcename, pkname, pksequencename, savedtablename, cardcustpanel, listcustpanel, dataconstraint, parent_pub_template, comefrom, ordersetting) values (s_pub_templet_1.nextval, 'T_APU_SWAPLOG', 'T_APU_SWAPLOG', 'V_L_APU_SWAPLOG', 'datasource_apms', 'ID', 'S_L_APU_SWAPLOG', 'L_APU_SWAPLOG', 'APMS', null, null, null, '0', 'SWAP_DATE DESC');
  END IF;
  -- 插入模板分组
 -- 插入模板明细
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'BASEORGID', '所属基地', '参照', null, 'select id id#,code code#,name 基地 from b_organization o where 1=1 and o.orglevel=1', 'Y', '1', 'N', null, null, null, null, '2', '120', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, 'N', null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'APU_ID', 'APU_ID', '文本框', null, null, 'Y', '3', 'N', null, null, null, null, '1000', '200', '200', 'N', '1', 'N', '1', null, null, 'N', null, null, null, null, null,null, null, null, 'N', null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'ID', 'ID', '数字框', null, null, 'Y', '2', 'N', null, null, null, null, '1', '145', '150', 'N', '1', 'N', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'AIRCRAFTID', '所属飞机', '参照', null, 'SELECT ID ID#,MSN MSN#,AIRCRAFTSN 飞机号 FROM B_AIRCRAFT ORDER BY AIRCRAFTSN ;ds=datasource_apms;', 'Y', '1', 'N', null, null, null, null, '2', '100', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'APUMODELID', 'APU型号', '下拉框', 'SELECT ID ID#,MODEL APUMODEL,SUBMODEL FROM B_APU_MODEL;ds=datasource_apms', 'SELECT ID ID#,MODEL APUMODEL,SUBMODEL FROM B_APU_MODEL;ds=datasource_apms', 'Y', '1', 'N', null, null, null, null, '3', '145', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'APUID', 'APU序号', '参照', null, 'SELECT ID ID#,APUSN CODE#,APUSN FROM B_APU T WHERE 1=1;ds=datasource_apms', 'Y', '1', 'N', null, null, null, null, '4', '100', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'SWAP_DATE', '拆换日期', '时间', null, null, 'Y', '1', 'N', null, null, null, null, '5', '145', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, '日期范围', null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'SWAP_DATE_STR', '拆换日期', '文本框', null, null, 'Y', '2', 'N', null, null, null, null, '6', '145', '150', 'N', '1', 'N', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'SWAP_REASON', '拆换原因', '下拉框', 'select d.value,d.valueen,d.valuecn from b_dictionary d where d.classname=''SWAPLOG'' and d.attributename=''SWAPREASON'';ds=datasource_apms', null, 'Y', '1', 'N', null, null, null, null, '7', '90', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'SWAP_ACTION', '拆换动作', '下拉框', 'select d.value,d.valueen,d.valuecn from b_dictionary d where d.classname=''SWAPLOG'' and d.attributename=''SWAPACTION'';ds=datasource_apms', null, 'Y', '1', 'N', null, null, null, null, '8', '80', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'FAULT_DESC', '故障说明', '多行文本框', null, null, 'Y', '2', 'N', null, null, null, null, '9', '180', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, '120', null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'TIME_ONINSTALL', 'APU装上时小时', '数字框', null, null, 'Y', '2', 'N', null, null, null, null, '10', '100', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'CYCLE_ONINSTALL', 'APU装上时循环', '数字框', null, null, 'Y', '2', 'N', null, null, null, null, '11', '100', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'TIME_ONREPAIRED', '修后时时间', '数字框', null, null, 'Y', '2', 'N', null, null, null, null, '12', '100', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'CYCLE_ONREPAIRED', '修后时循环', '数字框', null, null, 'Y', '2', 'N', null, null, null, null, '13', '100', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'TIME_ONREMOVE', 'APU拆下时小时', '数字框', null, null, 'Y', '2', 'N', null, null, null, null, '14', '100', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'CYCLE_ONREMOVE', 'APU拆下时循环', '数字框', null, null, 'Y', '2', 'N', null, null, null, null, '15', '100', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'REPAIR_FLAG', '是否送修过', '下拉框', 'SELECT VALUE,VALUEEN,VALUECN FROM B_DICTIONARY D WHERE D.CLASSNAME=''APU'' AND D.ATTRIBUTENAME=''REPAIR_FLAG'';ds=datasource_apms;', null, 'Y', '2', 'N', null, null, null, null, '16', '90', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'COMMENTS', '备注', '文本框', null, null, 'Y', '2', 'N', null, null, null, null, '17', '60', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'UPDATE_MAN', '更新人', '文本框', null, null, 'Y', '2', 'N', null, null, null, null, '18', '80', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_APU_SWAPLOG'), 'UPDATE_TIME', '更新时间', '时间', null, null, 'Y', '2', 'N', null, null, null, null, '19', '145', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, null, null, null, null, null, null, null, null, null);
  commit;
END;
/
-- ================end export of [T_APU_SWAPLOG]=============================================================
