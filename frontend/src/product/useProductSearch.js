import { useCallback, useEffect, useMemo, useState } from "react";
import { getProductList } from "../api-call/apis";

export const useProductSearch = () => {
  const defaultPagination = useMemo(() => {
    return {
      pageNumber: 0,
      limit: 10,
    };
  }, []);

  const [data, setData] = useState({ data: [], pagination: {} });
  const [loading, setLoading] = useState(false);
  const [filter, setFilter] = useState({});

  useEffect(() => {
    setLoading(true);
    getProductList(defaultPagination, filter).then((products) => {
      setData({
        pagination: products.pagination,
        data: products.data,
      });
      setLoading(false);
    });
  }, []);

  const handleChangePagination = useCallback(
    ({ pageNumber, limit }) => {
      setLoading(true);
      getProductList({ pageNumber, limit }, filter).then((products) => {
        setData({
          pagination: products.pagination,
          data: [...data.data, ...products.data],
        });
        setLoading(false);
      });
    },
    [data, filter]
  );

  const handleChangeFiltering = useCallback(
    ({ searchValue }) => {
      setFilter({ searchValue });
      setLoading(true);
      getProductList(defaultPagination, { searchValue }).then((products) => {
        setData({
          pagination: products.pagination,
          data: products.data,
        });

        setLoading(false);
      });
    },
    [data]
  );

  return {
    data,
    handleChangePagination,
    handleChangeFiltering,
    filter,
    loading,
  };
};
