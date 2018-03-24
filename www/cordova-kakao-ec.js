var exec = require('cordova/exec');

var Kakao = {
    sendTemplate: function (success, error, arg0) {
        exec(success, error, 'Kakao', 'sendTemplate', [arg0]);
    }
}

module.exports = Kakao;
