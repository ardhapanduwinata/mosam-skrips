const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    user: {
        type: String,
        required: true,
    },
    bank: {
        type: String,
        required: true,
    },
    date: {
        type: String,
        required: true
    },
    jmlbotolA: {
        type: Number
    },
    jumlbotolB: {
        type: Number
    },
    jmlgelas: {
        type: Number
    }
});

module.exports = mongoose.model('transaksiNabung', userSchema);