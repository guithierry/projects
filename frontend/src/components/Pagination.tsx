import { Pagination as BootstrapPagination } from "react-bootstrap";

export default function Pagination({
    totalPages,
    current,
    setCurrent,
}: {
    totalPages: number;
    current: number;
    setCurrent(current: number): void;
}) {
    return (
        <BootstrapPagination
            size="sm"
            className="mt-5 mb-5 d-flex justify-content-center"
        >
            {Array.from(Array(totalPages)).map((_, page) => (
                <BootstrapPagination.Item
                    key={page}
                    active={page === current}
                    onClick={() => setCurrent(page)}
                >
                    {page + 1}
                </BootstrapPagination.Item>
            ))}
        </BootstrapPagination>
    );
}
