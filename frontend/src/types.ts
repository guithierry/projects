interface BasicEntity {
    id: string;
    name: string;
    description: string;
    createdAt: Date;
    updatedAt: Date;
}

export type Status = "TODO" | "DOING" | "DONE";

export interface Project extends BasicEntity {
    status: Status;
}

export interface Todo extends BasicEntity {
    status: string;
    project: Project;
}
