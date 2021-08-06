<?php
require_once ('../../utils/utility.php');
require_once ('../../db/dbhelper.php');

$action = getPOST('action');

switch ($action) {
	case 'add':
		addFood();
		break;
	case 'update':
		updateFood();
		break;
	case 'delete':
		deleteFood();
		break;
	default:
		getFoodList();
		break;
}

function getFoodList() {
	$sql     = "select * from foods";
	$results = executeResult($sql);

	echo json_encode($results);
}

function deleteFood() {
	$id  = getPOST('id');
	$sql = "delete from foods where id = $id";
	execute($sql);

	$res = [
		'status' => '1',
		'msg'    => 'delete success!!!'
	];
	echo json_encode($res);
}

function addFood() {
	$title       = getPOST('title');
	$thumbnail   = getPOST('thumbnail');
	$description = getPOST('description');
	$updatedAt   = $createdAt   = date('Y-m-d H:i:s');

	$sql = "insert into foods (title, thumbnail, description, created_at, updated_at) values ('$title', '$thumbnail', '$description', '$createdAt', '$updatedAt')";
	execute($sql);

	//lay id cua food vua them vao la gi
	$sql     = "select * from foods where created_at = '$createdAt'";
	$results = executeResult($sql, true);

	$res = [
		'status' => '1',
		'msg'    => 'add success!!!',
		'id'     => $results['id']
	];
	echo json_encode($res);
}

function updateFood() {
	$id          = getPOST('id');
	$title       = getPOST('title');
	$thumbnail   = getPOST('thumbnail');
	$description = getPOST('description');
	$updatedAt   = date('Y-m-d H:i:s');

	$sql = "update foods set title = '$title', thumbnail = '$thumbnail', description = '$description' where id = '$id'";
	execute($sql);

	$res = [
		'status' => '1',
		'msg'    => 'update success!!!'
	];
	echo json_encode($res);
}