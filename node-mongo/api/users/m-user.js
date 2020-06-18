const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    email: {
        type: String,
        required: true,
    },
    name: {
        type: String,
        required: true,
    },
    password: {
        type: String,
        required: true
    },
    level: {
        type: String
    },
});

module.exports = mongoose.model('Users', userSchema);