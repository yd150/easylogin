//controllerクラスを作成する
//MVCのCにあたり、
//画面遷移機能に直結し、viewとmodelの架け橋になるクラス
//MVCとは

package com.example.easylogin.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.easylogin.model.dao.UserRepository;
import com.example.easylogin.model.entity.User;





@Controller
public class LoginController {
	
//	↓このアノを付与されたフィールドはnew演算子を
//	使うことなくインスタンス化される
	@Autowired
	UserRepository userRepos;
	
//	UserRepository is a raw type. References to generic type UserRepository<T> 
//	 should be parameterized
//	 UserRepositoryはrawタイプです。ジェネリック型UserRepository <T>への参照
//	 パラメータ化する必要があります
//	参照　http://did2.blog64.fc2.com/blog-entry-299.html
	
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
//	↑トップページへの繊維を担うindexメソッドを追加

	@RequestMapping("/login")
	public String login(
			@RequestParam("userName") String userName,
			@RequestParam("password") String password,
			Model m) {
		
//	↑Stringのデータを戻り値として返すloginというメソッドを作成している
//		引数は３つあるけど最初の二つにはあのが付与されている
//		このあのが付与された引数はクライアントからのリクエストであることを意味していて
//		HTML側で定義されたname属性を指定することで判断します
//		最後のmodelはレスポンスとしてクライアント側に返すためのオブジェクトである
//		今回はaddAtributeメソッドを使ってmessageというキー文字列に対してログイン結果によって
//		分岐するメッセージを値に設定している
		
		String message = "Welcome!";
		
		List<User> users = userRepos.findByUserNameAndPassword(userName,password);
//		↑userRepositryに追加したメソッドを呼び出してUserの一覧を取得している
		if(users.size()>0) {
			User user = users.get(0);
			message += user.getFullName();
		}else {
			message += "guest";
		}
		
		m.addAttribute("message",message);
		
		return "login";
	}
	
//	↑ログイン後のページへの遷移を担当するloginメソッドを作成している
	
	
	
	
	@ResponseBody
	public String showUsers() {
		
//		userrepositryのインスタンスからfindAllメソッドを呼び出してuser entityのリストを取得している
//		テキスト通り作成しているならDBにはレコードが１件しか存在してませんが
//		list型で取得する
//		その理由はfindAllメソッドの戻り値のデータ型がlistだから
		
		
		List<User> users = userRepos.findAll();
		
//		Type safety: The expression of type List needs unchecked conversion to conform 
//		 to List<User>
//		型の安全性：型リストの式は準拠するためにチェックされていない変換が必要です
//		 リストへ<ユーザー>
		
		
//		↓上で取得したリストの０番目を取得している
		
		User user = users.get(0);
		
//		↓userentityのインスタンスからuserNameとpasswordを連結した文字列を作成している
//		
		String info = user.getUserName() + " " + user.getPassword();
		
		return info;
	}
	
}
