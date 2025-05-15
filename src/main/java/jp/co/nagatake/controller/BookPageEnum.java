package jp.co.nagatake.controller;

import lombok.Getter;

/**
 * 各ページ要素
 */
@Getter
public enum BookPageEnum {
	LOGIN("/login")
	,HOME("/home")
	,OUTPUT("/output")
	,INPUT("/input")
	,UPDATE("/update")
	,ERROR("/error");
	
	private BookPageEnum(String path) {
		this.path = path;
	}

	// HTMLファイル相対パス
	private String path;

}
