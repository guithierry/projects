import { Modal } from "react-bootstrap";

export default function ModalContainer({
    children,
    title,
    visible,
    handleVisible,
}: {
    children: any;
    title: string;
    visible: boolean;
    handleVisible(): void;
}) {
    return (
        <Modal show={visible} onHide={handleVisible}>
            <Modal.Header closeButton>
                <Modal.Title>{title}</Modal.Title>
            </Modal.Header>

            <Modal.Body>{children}</Modal.Body>
        </Modal>
    );
}
