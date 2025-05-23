function actionFunc(val) {
	const url = /*[[ @{/} ]]*/ '';
		document.forms['updateDeleteForm'].action = baseUrl + val;
}

function clearSearchForm() {
	const form = document.querySelector('form');
	form.querySelector('input[name="title"]').value = '';
	form.querySelector('input[name="author"]').value = '';
	form.querySelector('input[name="publisher"]').value = '';
	form.submit();
}

function toggleFavorite(button) {
	const id = button.getAttribute('data-id');
	const currentFavorite = button.getAttribute('data-isFavorite') === 'true';
	const newFavorite = !currentFavorite;

	//POSTリクエスト用のフォームを動的に作成して送信
	const form = document.createElement('form');
	form.method = 'post';
	form.action = favoriteUrl;

	const inputId = document.createElement('input');
	inputId.type = 'hidden';
	inputId.name = 'id';
	inputId.value = id;
	form.appendChild(inputId);

	const inputFavorite = document.createElement('input');
	inputFavorite.type = 'hidden';
	inputFavorite.name = 'isFavorite';
	inputFavorite.value = newFavorite;
	form.appendChild(inputFavorite);

	document.body.appendChild(form);
	form.submit();
}

function confirmDelete() {
	return confirm("本当に削除してもいいですか?");
}