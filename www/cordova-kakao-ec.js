var exec = require('cordova/exec');

var Kakao = {
    sendTemplate: function (arg0, success, error) {
        exec(success, error, 'Kakao', 'sendTemplate', [arg0]);
    }
}

module.exports = Kakao;
