CREATE OR REPLACE PROCEDURE "RECOVER_IND_TABLE_PROC" (tablename varchar2) is
  v_tablename   varchar2(100);
  v_tmpstr   varchar2(100);
  v_uniqueness varchar2(100);
  v_indexname varchar2(100);
  v_columnname varchar2(100);
  i sys_refcursor;
  j sys_refcursor;
  v_count number;
begin
  select upper(tablename) into v_tablename from dual;
  --重建索引
  open i for 'select distinct bk.index_name,bk.uniqueness from TMP_INDEX_BK bk where table_name='''||v_tablename||'''';
  while true
  Loop
   fetch i into v_indexname,v_uniqueness;
   Exit When i%Notfound;
   --处理多列索引的情况
    v_tmpstr:='';
    open j for 'select distinct t.column_name from TMP_INDEX_BK t where table_name='''||v_tablename||''' and t.index_name='''||v_indexname||'''';
    while true
    loop
        fetch j into v_columnname;
        Exit When j%Notfound;
        v_tmpstr := v_tmpstr||v_columnname||',';
    end loop;
    close j;
    --去除最后一个逗号
    if v_tmpstr='' then
      return;
    else
      v_tmpstr := substr(v_tmpstr,0,length(v_tmpstr)-1);
    end if;

    if (v_uniqueness != 'UNIQUE') then
      v_uniqueness := '';
    end if;
    --zhangzy 20141025 判断是否已经有索引，个别情况索引已存在，不需要恢复
    v_count := 0;
    select count(1) into v_count from user_indexes t WHERE INDEX_NAME = v_indexname ;
    if v_count =0 then
       --只有在索引不存在的情况下才再创建
       execute immediate 'create ' || v_uniqueness || ' index ' ||
                      v_indexname || ' on ' || v_tablename || '(' ||
                      v_tmpstr || ')';
    end if;
      --清除索引备份信息
     execute immediate 'delete from TMP_INDEX_BK where table_name='''||v_tablename||''' and index_name='''||v_indexname||'''';
     commit;
  end loop;
  close i;
  --重启约束
  for i in (select u.constraint_name
              from user_constraints u
             where TABLE_name = v_tablename) loop
    execute immediate 'alter table ' || v_tablename ||
                      ' enable constraint ' || i.constraint_name;
  end loop;


end RECOVER_IND_TABLE_PROC;
/