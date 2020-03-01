-- 模板生成脚本[T_A_DFD_A46_LIST_DATA]
-- ================start===========================================================
-- 删除模板明细数据
delete pub_templet_1_item where pk_pub_templet_1 in (select pk_pub_templet_1 from pub_templet_1 where templetcode = 'T_A_DFD_A46_LIST_DATA');
-- 删除模板分组信息
delete from PUB_TEMPLET_1_ITEM_GROUP where templetid in (select pk_pub_templet_1 from pub_templet_1 where templetcode = 'T_A_DFD_A46_LIST_DATA');
-- 判断是否已经存在模板
DECLARE TEMPL_COUNT NUMBER(6);
BEGIN
  SELECT COUNT(*) INTO TEMPL_COUNT FROM pub_templet_1 where templetcode ='T_A_DFD_A46_LIST_DATA';
  IF TEMPL_COUNT > 0 THEN
    -- 更新老的模板，ID没有变化
    UPDATE PUB_TEMPLET_1 SET  templetcode='T_A_DFD_A46_LIST_DATA', templetname='T_A_DFD_A46_LIST_DATA', tablename='A_DFD_A46_LIST_DATA', datasourcename='datasource_apms', pkname=null, pksequencename='S_A_DFD_A46_LIST_DATA', savedtablename='A_DFD_A46_LIST_DATA', cardcustpanel='APMS', listcustpanel=null, dataconstraint=null, parent_pub_template=null, comefrom='0', ordersetting='GATHERTIME' WHERE templetcode = 'T_A_DFD_A46_LIST_DATA';
  ELSE
    -- 插入新模板，新生成ID
    Insert Into PUB_TEMPLET_1 (pk_pub_templet_1, templetcode, templetname, tablename, datasourcename, pkname, pksequencename, savedtablename, cardcustpanel, listcustpanel, dataconstraint, parent_pub_template, comefrom, ordersetting) values (s_pub_templet_1.nextval, 'T_A_DFD_A46_LIST_DATA', 'T_A_DFD_A46_LIST_DATA', 'A_DFD_A46_LIST_DATA', 'datasource_apms', null, 'S_A_DFD_A46_LIST_DATA', 'A_DFD_A46_LIST_DATA', 'APMS', null, null, null, '0', 'GATHERTIME');
  END IF;
  -- 插入模板分组
 -- 插入模板明细
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_A_DFD_A46_LIST_DATA'), 'MSG_NO', '报文编号', '文本框', null, null, 'Y', '3', 'N', null, null, null, null, '1', '80', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, 'N', null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_A_DFD_A46_LIST_DATA'), 'ENGQT1', '左发滑油量', '文本框', null, null, 'Y', '3', 'N', null, null, null, null, '2', '75', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, 'N', null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_A_DFD_A46_LIST_DATA'), 'ENGQT2', '右发滑油量', '文本框', null, null, 'Y', '3', 'N', null, null, null, null, '3', '75', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, 'N', null, null, null, null, null, null, null, null);
 Insert Into pub_templet_1_item (pk_pub_templet_1_item, pk_pub_templet_1, itemkey, itemname, itemtype, comboxdesc, refdesc, issave, isdefaultquery, ismustinput, loadformula, editformula, defaultvalueformula, colorformula, showorder, listwidth, cardwidth, listisshowable, listiseditable, cardisshowable, cardiseditable, bcolorformula, itemaction, ismustcondition, defaultcondition, condition_itemtype, condition_comboxdesc, condition_refdesc, condition_showorder, templetitemgroupid, clientrefdesc, extattr01, extattr02, extattr03, extattr04, extattr05, extattr06, extattr07, extattr08, extattr09, extattr10) values (s_pub_templet_1_item.nextval, (select PK_PUB_TEMPLET_1  From pub_templet_1 Where TEMPLETCODE = 'T_A_DFD_A46_LIST_DATA'), 'GATHERTIME', 'S1时间', '文本框', null, null, 'Y', '3', 'N', null, null, null, null, '4', '160', '150', 'Y', '1', 'Y', '1', null, null, 'N', null, null, null, null, null,null, null, null, 'N', null, null, null, null, null, null, null, null);
  commit;
END;
/
-- ================end export of [T_A_DFD_A46_LIST_DATA]=============================================================

