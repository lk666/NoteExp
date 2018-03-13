// IBookManager.aidl
// 第二类AIDL文件
// 作用是定义方法接口
package com.cn.lk.androidexp.ipc.binder;

// 导入所需要使用的非默认支持数据类型的包
import com.cn.lk.androidexp.ipc.binder.Book;

interface IBookManager {

    // 所有的返回值前都不需要加任何东西，不管是什么数据类型
    List<Book> getBookList();

    // 传参时除了Java基本类型以及String，CharSequence之外的类型，都需要在前面加上定向tag，表示数据流向
    // in：从客户端流向服务端，服务端改了数据不影响客户端
    // out：从服务端流向客户端，服务端收到的是空的对象（不是null），服务端改了数据会同步到客户端
    // inout：双向流动
    void addBook(in Book book);
}
