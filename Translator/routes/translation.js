var express = require('express');
var router = express.Router();
var request = require('request');
const uuidv4 = require('uuid/v4');
const fs = require('fs');

router.post('/', function(req, res, next) {
  translate(req)
      .then(translation => {
        res.json(translation);
      })
      .catch(err => next(err));

});

async function translateGoogle(req) {
    var optionsGoogle = {
        'method': 'POST',
        'url': 'https://translation.googleapis.com/language/translate/v2?key=AIzaSyBXKM-JWNY8bP7h2igWsc2O93yV1l5qLlg&access_token=MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDBmBA/RkcZz+Y8\\n/3CKJgnfz/jZ9BLBaEHu+dxrWjXoDAJ9kI3lsFYlhIHOEPpmswrSiZEunlk+1JV6\\nt3kI6Qr7PBs+eLaOy9s+ivfQCUUPpW4FaKBEgi+9c1qXk608+lJaStgpkFmNewag\\n+GXrhZGhfKWu8PFjqIrkUBjDKHUPcXbPQgo3pbG/oxpgLkPBT3JeAZsiDiEL2AFK\\nZSeXF5ekpQRME56MK1URHu4rOw88CJW2XDy8ohifmAX1gaYWBw/On4PKSsdNKtHM\\ndDjjxEZPZ6SH2W+gtffsy1wQfE+Q7c9Oj52Q6RkNd8YQO5qysT0ozsc7Biqvkirg\\nD1U2wU/NAgMBAAECggEAAYq+hmJ+6nu3b8DxGoiXB4tWhhXlmFjGPasL0GcVitWd\\np3ZhSvUOmpnllsiX1f2TFn42A9MLDu8Oib2wXxvMFJgz91lqytRg2KyusJ+CR0+g\\nArcEn8cRrkae8BojBRtRUJrMAfi+Xdwe/2U7Gpcjw8+EWlJc+dg70vm4WMWXuRnA\\ntp+AaOE9QQL/U1seXrfUr9uh8UU7G+Pbf25xWOxE+8ADcJgqLsAM6mvNrAAzCGwF\\nORXFJhJtp0+kcS7TA7s47FjkAr9cTGVOZMM065/Jg48HNQSa0REh6Elgo0M6/XZw\\niZCZvGNp7xdB3vONRri3b4f6LYh3dr6TJlRPOLZpwQKBgQDflmVdxrST+FDoX+jp\\nJGaB1R135PFHZI+lToLSU//2bLKxBXcOHD/Wy+nwCWxreXyDXr8Ehsz/6i7n/MMD\\n28UAZr6xjJ5lqcPo1yxeWL0eTZBOGcnOSLQdp1dEm05LkEclpg0YOHwOgdx6B3r4\\nVMnUqqpBsdXjabOqQH+RkY3jNQKBgQDdqJKamVQdp8+T6X5gk/veDSXztySCyh57\\nuDwwTRax4h/XZDsUZvpepvBJwMRTBrCJdoOaXzw4OTBZZfpeRuSPIF4xj2REVSDi\\nDvqFyHlbVPJs93ipWf5uqdmlXbREZfUhUaM7TBeMv/zo3eDj8v7fDaI2U+gwHp3R\\nlv/M89j1OQKBgDjxgcF/YPAqltiHzLbL+XtpfobRfQ20s8zjEPhmQlNNXbkt7iNe\\n2HdpGZxzmfRJTYfbsoSBnGY1C1s0CarK5wJyKHDpolG8CdQcMQWaThgQowbxFG+A\\nHrdKrjQf5bHi/eu9vaoEgLvlIPTHQO9MENcj3Ba9Jv8zJXnf0V2Ol3ddAoGAdiPM\\nz519WbkBSAKhGvSEv5seaUFpslPr32EYo13TBMhS1EhVvNAODbjIRWb6vI3KNdn2\\n1qZ/wC+q91sFEI6Ubzx/N8cHV+xPdG0rVXa+R9GEzFMx+oE+vNjGaf8Bf5sgBbwv\\nVgF+Y8RIFg18OcJf2Z64oGAkVtFaqETk3eXx8vECgYAKHHKrq/qa/6srppbPF6Yz\\nCaryFHqYGQtGL/aVn5cZc35pxvhK9PuRXtbpLxiUgX5amfhnFd+8Nce4jaZEoRcO\\nAnxI3gLFVO3pihRIpz2hQpu2NdZrK85A5FpZ8yh7ozmOR6F1PaF6a5qIH+MQjx8/\\nCaoGhVtTfxbvqvkSId0RCA==',
        'headers': {
            'Content-Type': 'application/json'
        },
        body: "{'q' :'" + addBackslash(req.body.q) + "','source' :'" + req.body.source + "','target' : '" + req.body.target + "'}"
    };
    return new Promise(function (resolve, reject) {
        request(optionsGoogle, function (error, response) {
            if (error) reject(error);
            resolve(JSON.parse(response.body));
        });
    });
}

function addBackslash(str) {
    return str.replace("'", "\\'");
}

async function translateMyMemory(req) {
    var request = require('request');
    var options = {
        'method': 'GET',
        'url': 'https://api.mymemory.translated.net/get?q=' + req.body.q + '&langpair=' + req.body.source + '|' + req.body.target + '&key=8c09c614f57c5b7c3365',
        'headers': {}
    };

    return new Promise(function (resolve, reject) {
        request(options, function (error, response) {
            if (error) reject(error);
            resolve(JSON.parse(response.body));
        });
    });

}


async function translateMicrosoft(req) {
  let options = {
    method: 'POST',
    baseUrl: 'https://api.cognitive.microsofttranslator.com/',
    url: 'translate',
    qs: {
      'api-version': '3.0',
      'to': [req.body.source, req.body.target]
    },
    headers: {
      'Ocp-Apim-Subscription-Key': '7e1c053053404dc5adababd74dc5b6cf',
      'Content-type': 'application/json',
      'X-ClientTraceId': uuidv4().toString()
    },
    body: [{
      'text': req.body.q
    }],
    json: true,
  };
  return new Promise(function (resolve, reject) {
    request(options, function (error, response) {
      if (error) reject(error);
      resolve(response.body);
    });
  });
}

async function translateYandex(req) {
    var request = require('request');
    var options = {
        'method': 'GET',
        'url': 'https://translate.yandex.net/api/v1.5/tr.json/translate?text=' + req.body.q + '&lang=' + req.body.target + '&key=trnsl.1.1.20200131T141847Z.79d4ab9390603adf.d1401ac778ff53fb6bdd143e330fb1ce1ef91eb3',
        'headers': {}
    };

    return new Promise(function (resolve, reject) {
        request(options, function (error, response) {
            if (error) reject(error);
            resolve(JSON.parse(response.body));
        });
    });
}

async function translate(req) {
    var response = {};
    var google = await translateGoogle(req);
    var microsoft = await translateMicrosoft(req);
    var myMemory = await translateMyMemory(req);
    var yandex = await translateYandex(req);
    response.source = req.body.source;
    response.target = req.body.target;
    response.text = req.body.q;

    response.translations = [];
    try {
        response.translations.push({api: getAPI('google'), translation: google.data.translations[0].translatedText});
    } catch (e) {
    }
    try {
        response.translations.push({api: getAPI('myMemory'), translation: myMemory.responseData.translatedText});
    } catch (e) {
    }
    try {
      response.translations.push({api: getAPI('microsoft'), translation : microsoft[0].translations[1].text});
    } catch (e) {
    }
    try {
        response.translations.push({api: getAPI('yandex'), translation : yandex.text[0]});
    } catch (e) {
    }


    return response;
}

function getAPI(name){
    let data = fs.readFileSync('mocks/ratings.json');
    let storage = data.toString().trim();
    storage = JSON.parse(storage);
    for (let api of storage) {
        if (api['name'] === name){
            return api;
        }
    }
}


module.exports = router;
