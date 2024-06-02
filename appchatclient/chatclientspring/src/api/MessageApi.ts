import { Message } from "../model/Message";
import { User } from "../model/User";
import { request } from "./UserApi";

export async function getAllMessageBySenderId(id: number): Promise<Message[]> {
    // const result: User[];
    const url = "http://localhost:8080/api/messages/sender/" + id;
    const messages = await request(url);
    console.log(messages);
    return messages.map((message: Message) => {
        return new Message(message.id, message.senderId, message.receiverId, message.message, message.senderName, message.receiverName);

    });
}
export async function getAllMessageByReceiverId(id: number): Promise<Message[]> {
    // const result: User[];
    const url = "http://localhost:8080/api/messages/receiver/" + id;
    const messages = await request(url);
    console.log(messages);
    return messages.map((message: Message) => {
        return new Message(message.id, message.senderId, message.receiverId, message.message, message.senderName, message.receiverName);
    });
}
export async function getAllMessageBySenderIdAndReceiverId(senderId: number, receiverId: number): Promise<Message[]> {
    const url = "http://localhost:8080/api/messages/sender/" + senderId + "/receiver/" + receiverId;
    const messages = await request(url);
    console.log(messages);
    return messages.map((message: Message) => {
        return new Message(message.id, message.senderId, message.receiverId, message.message, message.senderName, message.receiverName);
    });
}
