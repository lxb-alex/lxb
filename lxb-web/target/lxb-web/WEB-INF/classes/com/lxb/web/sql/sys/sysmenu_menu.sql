-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `permission_id`, `type`, `icon`, `order_num`, `create_date`)
    VALUES ('1', '目录表', 'generator/sys/menu.html', NULL, '1', 'fa fa-file-code-o', '6', '2017/11/13');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `permission_id`, `type`, `icon`, `order_num`, `create_date`)
    SELECT @parentId, '查看', null, 'sys/menu:list,sys/menu:info', '2', null, '6', '2017/11/13';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `permission_id`, `type`, `icon`, `order_num`, `create_date`)
    SELECT @parentId, '新增', null, 'sys/menu:save', '2', null, '6', '2017/11/13';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `permission_id`, `type`, `icon`, `order_num`, `create_date`)
    SELECT @parentId, '修改', null, 'sys/menu:update', '2', null, '6', '2017/11/13';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `permission_id`, `type`, `icon`, `order_num`, `create_date`)
    SELECT @parentId, '删除', null, 'sys/menu:delete', '2', null, '6', '2017/11/13';
