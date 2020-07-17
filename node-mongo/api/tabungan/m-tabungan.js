const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Users",
        required: true,
    },
    jmlbotolA: {
        type: Number,
        default: 0
    },
    jmlbotolB: {
        type: Number,
        default: 0
    },
    jmlgelas: {
        type: Number,
        default: 0
    },
});

module.exports = mongoose.model('Tabungan', userSchema);