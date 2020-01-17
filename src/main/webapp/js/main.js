var main = {};

main.selectedFileArea = null;
main.fileListArea = null;
main.uploadButton = null;
main.textArea = null;
main.selectButton = null;
main.saveButton = null;
main.fileInput = null;
main.textInfoArea = null;

main.onReady = function() {
  if (window.dbg) {
    util.http.log = true;
  }
  main.selectedFileArea = util.el('#selected-file');
  main.fileInput = util.el('#files');
  main.selectButton = util.el('#select-button');
  main.fileListArea = util.el('#file-list');
  main.uploadButton = util.el('#upload-button');
  main.textArea = util.el('#text');
  main.saveButton = util.el('#save-button');
  main.textInfoArea = util.el('#text-info');

  main.selectButton.addEventListener('click', main.onSelectClick);
  main.fileInput.addEventListener('change', main.onFileSelect);
  main.uploadButton.addEventListener('click', main.onUploadClick);

  util.textarea.addStatusInfo(main.textArea, main.textInfoArea);
  util.addKeyHandler('down', 83, main.onCtrlS, {ctrl: true});

  main.init();
};

main.init = function() {
  main.onCancelFileSelect();
  main.getFileList();
  main.getText();
};

main.onFileNameClick = function() {
  main.selectFile();
};

main.onSelectClick = function() {
  main.selectFile();
};

main.selectFile = function() {
  main.fileInput.click();
};

main.onFileSelect = function(e) {
  if (e.target.files.length == 0) {
    main.onCancelFileSelect();
    return;
  }
  var file = e.target.files[0];
  var html = file.name + '  <span style="color:#ccc;">' + util.formatDec(file.size) + ' bytes</span>';
  util.writeHTML(main.selectedFileArea, html);
  main.uploadButton.disabled = false;
};

main.onCancelFileSelect = function() {
  var html = '<span style="color:#888;">NO FILE SELECTED</span>';
  util.writeHTML(main.selectedFileArea, html);
  main.uploadButton.disabled = true;
};

main.onUploadClick = function() {
  document.f1.submit();
};

main.getFileList = function() {
  var req = {
    url: 'main',
    method: 'POST',
    data: {
      action: 'getFileList'
    }
  };
  util.http(req, main.getFileListCb);
};
main.getFileListCb = function(xhr, res, req) {
  if (xhr.status != 200) {
    log.e(xhr.status);
    util.infotip.show(xhr.status);
    return;
  }
  var obj = util.fromJSON(res);
  if (obj.status != 'OK') {
    log.e(obj.status);
    util.infotip.show(obj.status);
    return;
  }
  var fileList = obj.body;

  if (fileList.length == 0) {
    main.fileListArea.innerHTML = '<span style="display:inline-block;color:#888;margin-top;2px;">NO FILES</span>';
    return;
  }

  var html = '<table>';
  for (var i = 0; i < fileList.length; i++) {
    var file = fileList[i];
    html += '<tr>';
    html += '<td>';
    html += '<span class="pseudo-link white" onclick="main.download(\'' + file.name + '\')" data-tooltip="Download">';
    html += file.name;
    html += '</span>';
    html += '</td>';
    html += '<td style="text-align:right;padding-left:1em;">';
    html += '<span style="color:#ccc;">' + util.formatDec(file.size) + '</span>';
    html += '</td>';
    html += '<td>';
    html += '<span class="pseudo-link red" style="margin-left:0.8em;" onclick="main.deleteFile(\'' + file.name + '\')" data-tooltip="Delete">X</span>';
    html += '</td>';
    html += '</tr>';
  }
  html += '</table>';
  util.writeHTML(main.fileListArea, html);
};


main.deleteAll = function() {
  util.dialog.yesno('Delete all files?', main._deleteAll);
};
main._deleteAll = function(yes) {
  if (!yes) {
    return;
  }
  var param = null;
  main.execServerAction('deleteAll', param, main.deleteAllCb);
};
main.deleteAllCb = function(xhr, res, req) {
  main.init();
};

main.deleteTargetFile = null;
main.deleteFile = function(file) {
  main.deleteTargetFile = file;
  util.dialog.yesno('Delete?', main._deleteFile);
};

main._deleteFile = function(yes) {
  if (!yes) {
    return;
  }
  var param = {
    file: main.deleteTargetFile
  };
  main.execServerAction('deleteFile', param, main.deleteFileCb);
};
main.deleteFileCb = function(xhr, res, req) {
  main.getFileListCb(xhr, res, req);
};

main.getText = function() {
  var param = null;
  main.execServerAction('getText', param, main.getTextCb);
};
main.getTextCb = function(xhr, res, req) {
  if (xhr.status != 200) {
    log.e(xhr.status);
    util.infotip.show(xhr.status);
    return;
  }
  var obj = util.fromJSON(res);
  if (obj.status != 'OK') {
    log.e(obj.status);
    util.infotip.show(obj.status);
    return;
  }
  var text = util.decodeBase64(obj.body);
  main.textArea.value = text;
};

main.save = function() {
  var text = main.textArea.value;
  var b64Text = util.encodeBase64(text);
  var param = {
    text: b64Text
  };
  main.execServerAction('saveText', param, main.saveTextCb);
};
main.saveTextCb = function(xhr, res, req) {
  if (xhr.status != 200) {
    log.e(xhr.status);
    return;
  }
  var obj = util.fromJSON(res);4
  if (obj.status != 'OK') {
    log.e(obj.status);
    util.infotip.show(obj.status);
    return;
  }
  main.infotip(obj.status);
};
main.clear = function() {
  util.dialog.yesno('Clear?', main._clear);
};
main._clear = function(yes) {
  if (!yes) return;
  var param = {
    text: ''
  };
  main.execServerAction('saveText', param, main.saveTextCb);
  main.textArea.value = '';
};

main.copy = function() {
  util.copy2clpbd(main.textArea.value);
  main.infotip('OK');
};

main.download = function(fileName) {
  var param = {
    action: 'download',
    file: fileName
  };
  util.submit('main', 'POST', param);
};

main.execServerAction = function(action, param, cb) {
  var data = {
    action: action,
  };
  for (var k in param) {
    data[k] = param[k];
  }
  var req = {
    url: 'main',
    method: 'POST',
    data: data
  };
  util.http(req, cb);
};

main.infotip = function(msg, center) {
  msg += '';
  var pos = {x: 215, y: 105};
  if (center) {
    pos = null;
  }
  util.infotip.show(msg, 1000, {pos: pos, style: {'font-size': '16px'}});
};

main.onCtrlS = function(e) {
  main.save();
  e.preventDefault();
};

window.addEventListener('DOMContentLoaded', main.onReady, true);
