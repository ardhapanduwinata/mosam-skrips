const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Users",
        require: true
    },
    bank: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Users",
        required: true,
    },
    date: {
        type: String,
        default: null,
        required: true
    },
    jmlbotolA: {
        type: Number
    },
    jmlbotolB: {
        type: Number
    },
    jmlgelas: {
        type: Number
    }
});

module.exports = mongoose.model('transaksiNabung', userSchema);