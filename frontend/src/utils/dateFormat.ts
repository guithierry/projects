import { format, isToday, isYesterday } from "date-fns";

export function dateFormat(date: Date) {
    const commentDate = new Date(date);

    if (isYesterday(commentDate)) {
        return `Yesterday at ${format(commentDate, "kk:mm aa")}`;
    }

    if (isToday(commentDate)) {
        return `Today at ${format(commentDate, "kk:mm aa")}`;
    }

    return format(commentDate, "dd/MM/yyyy");
}
