<?php
function removeSpecialCharacter($str) {
	$str = str_replace('\\', '\\\\', $str);
	$str = str_replace('\'', '\\\'', $str);
	//$str = abc'okok => abc'okok => abc\'okok
	//$str = abc\'okok => abc\\'okok => abc\\\'okok
	return $str;
}

function getPost($key) {
	//Phan 1: Lay du lieu tu $_POST ($_POST => mang quan ly $key => $value)
	$value = '';
	if (isset($_POST[$key])) {
		$value = $_POST[$key];
	}
	// return $value;
	//Phan 2: Ham xu ly ky tu dac biet
	return removeSpecialCharacter($value);
}

function getGet($key) {
	$value = '';
	if (isset($_GET[$key])) {
		$value = $_GET[$key];
	}

	return removeSpecialCharacter($value);
}

function getMd5Security($pwd) {
	return md5(md5($pwd).MD5_PRIVATE_KEY);
}

function checkLogin() {
	$token = '';
	if (isset($_COOKIE['token'])) {
		$token = $_COOKIE['token'];
		$sql   = "select * from users where token = '$token'";
		$user  = executeResult($sql, true);

		return $user;
	}
	return null;
}

function signout() {
	$token = '';
	if (isset($_COOKIE['token'])) {
		$token = $_COOKIE['token'];
		$sql   = "update users set token = null where token = '$token'";
		// echo $sql;
		execute($sql);
		// die();
	}
}