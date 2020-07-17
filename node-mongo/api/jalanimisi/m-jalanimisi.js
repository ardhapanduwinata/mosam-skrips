const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    misi: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Misi",
        require: true
    },
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Users",
        require: true
    },
    targettercapai: {
        type: Number,
        default: 0
    },
    dateselesai: {
        type: String,
        default: "00/00/0000"
    },
    status: {
        type: String,
        default: "belum",
        require: true
    },
    claimpoin: {
        type: String,
        default: "belum",
        require: true
    }
});

module.exports = mongoose.model('JalaniMisi', userSchema);