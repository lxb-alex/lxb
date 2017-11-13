-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `permission_id`, `type`, `icon`, `order_num`, `create_date`)
    VALUES ('1', '系统用户表', 'generator/sys/user.html', NULL, '1', 'fa fa-file-code-o', '6', '2017/11/13');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `permission_id`, `type`, `icon`, `order_num`, `create_date`)
    SELECT @parentId, '查看', null, 'sys/user:list,sys/user:info', '2', null, '6', '2017/11/13';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `permission_id`, `type`, `icon`, `order_num`, `create_date`)
    SELECT @parentId, '新增', null, 'sys/user:save', '2', null, '6', '2017/11/13';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `permission_id`, `type`, `icon`, `order_num`, `create_date`)
    SELECT @parentId, '修改', null, 'sys/user:update', '2', null, '6', '2017/11/13';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `permission_id`, `type`, `icon`, `order_num`, `create_date`)
    SELECT @parentId, '删除', null, 'sys/user:delete', '2', null, '6', '2017/11/13';
