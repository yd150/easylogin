//respositryインターフェイスを作成する
//springbootでのrespositrytpはDBへの基本的なアクセスを
//実装してくれる便利なインターフェイス

package com.example.easylogin.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.easylogin.model.entity.User;

//↓このアノはdbへの基本的なアクセスを自動で実装してくれるインターフェイス
@Repository
//<>のuserは、今回作成しているresponsitryはuserテーブルのentityに
//アクセスするためのものなので、１つ目にはUserを指定し
//２つ目にはuserテーブルのIDの型であるLongを指定する
public interface UserRepository<T> extends JpaRepository<User, Long> {

	List<User> findByUserNameAndPassword(String userName, String password);
}