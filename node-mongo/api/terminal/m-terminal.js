const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    namaTerminal: {
        type: String,
        required: true,
    },
    alamatTerminal: {
        type: String,
        required: true
    }
});

module.exports = mongoose.model('Terminal', userSchema);