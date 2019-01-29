function show(content) {
	window.document.getElementById('app').innerText = 'Hello, ' + content;
}

/*CommonJS规范导出show函数*/
module.exports = show;