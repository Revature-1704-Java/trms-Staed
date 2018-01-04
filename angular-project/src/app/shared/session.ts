export class ThisSession {
    public store(key: string, value: string): void {
        sessionStorage.setItem(key, value);
    }

    public retrieve(key: string): string {
        return sessionStorage.getItem(key);
    }

    public exists(key: string): boolean {
        if (sessionStorage.getItem(key) !== null) {
            return true;
        } else {
            return false;
        }
    }

    public checkEqual(key: string, value: string): boolean {
        if (sessionStorage.getItem(key) === value) {
            return true;
        } else {
            return false;
        }
    }

    public clear(): void {
        sessionStorage.clear();
    }
}
