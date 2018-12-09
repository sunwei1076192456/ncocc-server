package com.tz_tech.module.menu.manager;

import com.tz_tech.module.common.model.Menu;
import com.tz_tech.module.common.model.User;
import com.tz_tech.module.menu.dao.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class MenuManager {

    @Autowired
    private MenuDao menuDao;

    /*
     * 排序,根据sort排序
     */
    public Comparator<Menu> sort(){
        Comparator<Menu> comparator = new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                if(o1.getSort() != o2.getSort()){
                    return o1.getSort() - o2.getSort();
                }
                return 0;
            }
        };
        return comparator;
    }


    public Map<String,Object> findTree(User user) {
        Map<String,Object> data = new HashMap<String,Object>();
        try {//查询该用户下的所有菜单
            List<Menu> allMenu = menuDao.findTreeByLoginName(user);
            //根节点
            List<Menu> rootMenu = new ArrayList<Menu>();
            for (Menu nav : allMenu) {
                if(nav.getParent_id() == 0){//父节点是0的，为根节点。
                    rootMenu.add(nav);
                }
            }
            /* 根据Menu类的order排序 */
            Collections.sort(rootMenu, sort());
            //为根菜单设置子菜单，getClild是递归调用的
            for (Menu nav : rootMenu) {
                /* 获取根节点下的所有子节点 使用getChild方法*/
                List<Menu> childList = getChild(nav.getId(), allMenu);
                nav.setChildren(childList);//给根节点设置子节点
            }
            /**
             * 返回构建好的菜单数据。
             */
            data.put("allMenu", rootMenu);
        } catch (Exception e) {
            data.put("allMenu", new ArrayList());
        }
        return data;
    }


    /**
     * 获取子节点
     * @param id 父节点id
     * @param allMenu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    public List<Menu> getChild(int id, List<Menu> allMenu){
        //子菜单
        List<Menu> childList = new ArrayList<Menu>();
        for (Menu nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if(nav.getParent_id() == id){
                childList.add(nav);
            }
        }
        //递归
        for (Menu nav : childList) {
            nav.setChildren(getChild(nav.getId(), allMenu));
        }
        Collections.sort(childList,sort());//排序
        //如果节点下没有子节点，返回一个空List（递归退出）
        if(childList.size() == 0){
            return new ArrayList<Menu>();
        }
        return childList;
    }
}
