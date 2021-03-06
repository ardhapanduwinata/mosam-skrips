const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Users",
        required: true,
    },
    jmlPoin: {
        type: Number,
        default: 0
    },
    jmlVoucher: {
        type: Number,
        default: 0
    },
    badge: {
        type: String,
        default: "Bronze"
    }
});

module.exports = mongoose.model('Poin', userSchema);