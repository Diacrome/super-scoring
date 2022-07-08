export interface TestCatalogInfo {
  testMap: TestCatalog;
  found: number;
  page: number;
  perPage: number;
}

export type TestCatalog = Record<number, TestCard>;

export interface TestCard {
  id: number;
  name: string;
  description: string;
}
