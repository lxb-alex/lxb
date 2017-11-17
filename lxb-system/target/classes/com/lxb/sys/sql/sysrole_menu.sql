-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `permission_id`, `type`, `icon`, `order_num`, `create_date`)
    VALUES ('1', '角色表', 'generator/sys/role.html', NULL, '1', 'fa fa-file-code-o', '6', '2017/11/15');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `permission_id`, `type`, `icon`, `order_num`, `create_date`)
    SELECT @parentId, '查看', null, 'sys/role:list,sys/role:info', '2', null, '6', '2017/11/15';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `permission_id`, `type`, `icon`, `order_num`, `create_date`)
    SELECT @parentId, '新增', null, 'sys/role:save', '2', null, '6', '2017/11/15';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `permission_id`, `type`, `icon`, `order_num`, `create_date`)
    SELECT @parentId, '修改', null, 'sys/role:update', '2', null, '6', '2017/11/15';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `permission_id`, `type`, `icon`, `order_num`, `create_date`)
    SELECT @parentId, '删除', null, 'sys/role:delete', '2', null, '6', '2017/11/15';
