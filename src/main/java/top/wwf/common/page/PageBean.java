package top.wwf.common.page;

import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.wwf.common.consts.HttpResponseEnum;
import top.wwf.common.exception.MyException;

import java.io.Serializable;
import java.util.List;

/**
* @Description:    分页查询的结果
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-03-04 18:12
*/
public class PageBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;
//    private static Logger logger= LoggerFactory.getLogger(PageBean.class);

    //总页数
    private int     pages;
    //总的记录数
    private Long count;
    //结果集
    private List<T> list;
    //当前页，从1开始
    private int     nowPage;
    //前一页
    private int     prePage;
    //下一页
    private int     nextPage;
    private boolean isByPage=false;

//    public PageBean(List<T> list){
//        if (list instanceof Page){
//            Page<T> page =(Page<T>)list;
//            this.pages=page.getPages();
//            this.list=page;
//            this.nowPage=page.getPageNum();
//            this.prePage=this.nowPage==1 ? 1 : (this.nowPage-1);
//            this.nextPage=this.nowPage==this.pages ? this.pages : (this.nowPage+1);
//        }else {
//            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR,"请确保查询前已经调用了PageHelper进行了分页设置");
//        }
//    }

    public static<T> PageBean createByPage(List<T> list){
        if (list instanceof Page){
            PageBean pageBean=new PageBean();
            Page<T> page =(Page<T>)list;
            pageBean.isByPage=true;
            pageBean.pages=page.getPages();
            pageBean.list=page;
            pageBean.pages=page.getPages();
            pageBean.nowPage=page.getPageNum();
            pageBean.count=page.getTotal();
            pageBean.prePage=pageBean.nowPage==1 ? 1 : (pageBean.nowPage-1);
            pageBean.nextPage=pageBean.nowPage>=pageBean.pages ? pageBean.nowPage : (pageBean.nowPage+1);
            return pageBean;
        }else {
//            logger.error("请确保查询前已经调用了PageHelper进行了分页设置");
            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR,"请确保查询前已经调用了PageHelper进行了分页设置");
        }
    }

    public static <T> PageBean createByList(List<T> list){
        PageBean<T> pageBean=new PageBean();
        pageBean.pages=1;
        pageBean.list=list;
        pageBean.nowPage=1;
        pageBean.nextPage=1;
        pageBean.prePage=1;
        pageBean.isByPage=false;
        return pageBean;
    }

//    public int getPages() {
//        return pages;
//    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
//        if (isByPage){
//            this.list=list;
//        }else {
//            throw new MyException(HttpResponseEnum.INTERNAL_SERVER_ERROR,"由List创建的PageBean不允许重复设置List");
//        }
        this.list=list;
//        if (list instanceof Page) {
//            Page<T> page = (Page<T>) list;
//            this.pages = page.getPages();
//            this.list = page;
//            this.nowPage = page.getPageNum();
//            this.prePage = this.nowPage == 1 ? 1 : (this.nowPage - 1);
//            this.nextPage = this.nowPage == this.pages ? this.pages : (this.nowPage + 1);
//        }else {
//            this.list = list;
//        }
    }

    public int getPrePage() {
        return prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getNowPage() {
        return nowPage;
    }

    public int getPages() {
        return pages;
    }

    public Long getCount() {
        return count;
    }
}
