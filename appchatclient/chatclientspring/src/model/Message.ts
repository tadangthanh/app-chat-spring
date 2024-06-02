export class Message {
    id: number;
    senderId: number;
    receiverId: number;
    message: string;
    senderName: string;
    receiverName: string;

    constructor(id: number, senderId: number, receiverId: number, message: string, senderName: string, receiverName: string) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }
}