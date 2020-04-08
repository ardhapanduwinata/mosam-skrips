function cobain(req, res, next) {
    const lanjut = false;
    console.log('gak masuk sini?');
    if (lanjut == true) {
        console.log('masuk sini gak?');
        next();
    } else {
        console.log('SORRY ANDA GABISA LANJUT!');
        res.send('udah hop');
    }
}

module.exports = cobain;