import { User } from "../types";
import ModalContainer from "./ModalContainer";

export default function ProjectUsersModal({
    users,
    visible,
    handleVisible,
}: {
    users: User[];
    visible: boolean;
    handleVisible(): void;
}) {
    return (
        <ModalContainer
            title="Users"
            visible={visible}
            handleVisible={handleVisible}
        >
            <div
                className="overflow-styles"
                style={{
                    maxHeight: 400,
                    overflow: "hidden scroll",
                }}
            >
                {users &&
                    users.map(({ name, email }) => (
                        <div
                            className="pt-1 pb-2 d-flex align-items-center border-bottom"
                            key={email}
                        >
                            <div
                                className="d-flex align-items-center justify-content-center"
                                style={{
                                    width: 50,
                                    height: 50,
                                    borderLeft: "1px solid white",
                                    borderRadius: "50%",
                                    color: "white",
                                    background: "#929292",
                                    flexGrow: 0,
                                    flexShrink: 0,
                                }}
                            >
                                {name.charAt(0).toUpperCase()}
                            </div>
                            <div className="ms-2 d-flex flex-column">
                                <strong>{name}</strong>
                                <span>{email}</span>
                            </div>
                        </div>
                    ))}
            </div>
        </ModalContainer>
    );
}
