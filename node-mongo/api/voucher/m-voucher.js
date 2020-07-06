const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    user: {
        type: String,
        required: true,
    },
    jmlVoucher: {
        type: Number,
        default: 0
    }
});

module.exports = mongoose.model('Voucher', userSchema);