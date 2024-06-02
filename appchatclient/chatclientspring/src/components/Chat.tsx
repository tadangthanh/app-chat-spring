import React from 'react';
import '.././index.css';

interface ChatSectionProps {
    name: string;
    message: string;
    isSender: boolean; // Thêm prop isSender
}

const ChatSection: React.FC<ChatSectionProps> = ({ name, message, isSender }) => {
    return (
        <li className={`chat-section d-flex mb-4 ${isSender ? 'justify-content-end' : 'justify-content-start'}`}>
            <div className={`card ${isSender ? 'bg-success' : ''} `}>
                <div className="card-header">
                    <p className="fw-bold mb-0">{name}</p>
                </div>
                <div className="card-body">
                    <p className={`mb-0 ${isSender ? 'text-white' : ''} `}>{message}</p>
                </div>
            </div>
        </li>
    );
}

export default ChatSection;
