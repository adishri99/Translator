var express = require('express');
var router = express.Router();
var request = require('request');
const uuidv4 = require('uuid/v4');

const fs = require('fs');

router.post('/like', function (req, res, next) {
    try {
        res.status(200).json(addLike(req))
    } catch (err) {
        res.status(404).json(err);
    }
});

router.delete('/like', function (req, res, next) {
    try {
        res.status(200).json(removeLike(req))    //removelike created below
    } catch (err) {
        res.status(404).json(err);
    }
});



router.post('/dislike', function (req, res, next) {
    try {
        res.status(200).json(addDislike(req))
    } catch (err) {
        res.status(404).json(err);
    }
});

router.delete('/dislike', function (req, res, next) {
    try {
        res.status(200).json(removeDislike(req))
    } catch (err) {
        console.log(err);
        res.status(404).json(err);
    }
});


function addLike(req) {
    let data = fs.readFileSync('mocks/ratings.json');
    let storage = data.toString().trim();
    storage = JSON.parse(storage);
    let find = false;
    for (let api of storage) {
        if (api['name'] === req.body.name) {
            api['likes'] = api['likes'] + 1;
            find = true;
        }
    }
    if (!find) throw new Error('Cannot like' + req.body.name + ': not found');
    fs.writeFileSync('mocks/ratings.json', JSON.stringify(storage), 'utf8');
    return storage;
}

function addDislike(req) {
    let data = fs.readFileSync('mocks/ratings.json');
    let storage = data.toString().trim();
    storage = JSON.parse(storage);
    let find = false;
    for (let api of storage) {
        if (api['name'] === req.body.name) {
            api['dislikes'] = api['dislikes'] + 1;
            find = true;
        }
    }
    if (!find) throw new Error('Cannot like' + req.body.name + ': not found');
    fs.writeFileSync('mocks/ratings.json', JSON.stringify(storage), 'utf8');
    return storage;
}


function removeLike(req) {
    let data = fs.readFileSync('mocks/ratings.json');
    let storage = data.toString().trim();
    storage = JSON.parse(storage);
    let find = false;
    for (let api of storage) {
        if (api['name'] === req.headers.name) {
            api['likes'] = api['likes'] - 1;            //removing the like
            find = true;
        }
    }
    if (!find) throw new Error('Cannot like' + req.body.name + ': not found');
    fs.writeFileSync('mocks/ratings.json', JSON.stringify(storage), 'utf8');
    return storage;
}

function removeDislike(req) {
    let data = fs.readFileSync('mocks/ratings.json');
    let storage = data.toString().trim();
    storage = JSON.parse(storage);
    let find = false;
    for (let api of storage) {
        if (api['name'] === req.headers.name) {
            api['dislikes'] = api['dislikes'] - 1;
            find = true;
        }
    }
    if (!find){
        throw new Error('Cannot like' + req.body.name + ': not found');
    }
    fs.writeFileSync('mocks/ratings.json', JSON.stringify(storage), 'utf8');
    return storage;
}
module.exports = router;
