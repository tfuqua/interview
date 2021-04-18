import axios from "axios";

export const getProductList = (pagination, filter) => {
  return axios({
    method: "GET",
    url: "api/products",
    params: {
      limit: pagination.limit,
      pageNumber: pagination.pageNumber,
      searchValue: filter.searchValue,
    },
  }).then((res) => {
    return res.data;
  });
};
